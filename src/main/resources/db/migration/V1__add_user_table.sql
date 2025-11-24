-- ==============================================
-- USERS TABLE
-- ==============================================
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    phone      VARCHAR(20) NULL,
    provider   VARCHAR(50)  NOT NULL DEFAULT 'LOCAL', -- LOCAL / KAKAO

    status     VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE', -- ACTIVE / INACTIVE / BLOCKED

    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_users_email ON users (email);


-- ==============================================
-- USER ADDRESS TABLE
-- ==============================================
CREATE TABLE user_address
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT       NOT NULL,

    address_name VARCHAR(100) NOT NULL,
    recipient    VARCHAR(100) NOT NULL,
    phone        VARCHAR(20)  NOT NULL,

    postal_code  VARCHAR(20)  NOT NULL,
    address1     VARCHAR(255) NOT NULL,
    address2     VARCHAR(255) NULL,

    is_default   TINYINT(1) NOT NULL DEFAULT 0,

    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_address_user_id FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_user_address_user_id ON user_address (user_id);

-- MariaDB는 partial index를 지원하지 않기 때문에
-- 기본 배송지는 아래와 같이 UNIQUE INDEX + 트리거 조합으로 구현해야 한다.
CREATE UNIQUE INDEX idx_user_default_address
    ON user_address (user_id, is_default);


-- ==============================================
-- LOGIN HISTORY TABLE
-- ==============================================
CREATE TABLE login_history
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT      NOT NULL,

    ip         VARCHAR(50) NOT NULL,
    user_agent VARCHAR(500) NULL,
    login_at   DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_login_history_user_id FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_login_history_user_id ON login_history (user_id);
