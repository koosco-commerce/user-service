# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Key Components

### Registration Flow
1. `POST /api/users` - Creates user record in user-service database
2. Calls auth-service via Feign client to create auth credentials
3. If auth-service call fails, user record is rolled back in a new transaction
4. Uses `TransactionRunner` for explicit transaction management during rollback

### Value Objects
- **Email**: Inline value class with regex validation (`^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$`)
- **Phone**: Inline value class with Korean mobile format validation (`^010-\\d{3,4}-?\\d{4}$`), nullable

### User Status State Machine
- `ACTIVE` - Default status for new users, can transition via `quit()` or `forceDelete()`
- `INACTIVE` - User quit their account, set via `quit()`, can reactivate via `activate()`
- `BLOCKED` - Admin-deleted user via `forceDelete()`, throws `ConflictException` if already blocked

### Auth Provider
- `LOCAL` - Default, email/password registration
- `KAKAO` - OAuth provider (prepared for social login)

### API Endpoints

**UserController** (`/api/users`):
- `POST /` - Register new user (public)
- `GET /{userId}` - Get user details
- `DELETE /me` - Self-delete (sets INACTIVE)
- `PATCH /me` - Self-update (name, phone)

**AdminController** (`/api/users`):
- `DELETE /{userId}` - Force delete user (ADMIN only, sets BLOCKED)
- `PATCH /{userId}` - Force update user (ADMIN only)

### Public Endpoints
Configured in `PublicEndpoints` implementing `PublicEndpointProvider`:
- `/api/users` - Registration endpoint
- `/api/users/login`
- `/api/users/**`

### Database
- MariaDB with Flyway migrations in `src/main/resources/db/migration/`
- Tables: `users`, `user_address`

## Dependencies

Requires GitHub Packages authentication for common modules:
- `common-core` - Shared exceptions, annotations, API response wrapper, TransactionRunner
- `common-security` - JWT validation, security configuration, AuthId resolver

Set `GH_USER` and `GH_TOKEN` environment variables or configure in `~/.gradle/gradle.properties`:
```properties
gpr.user=your-github-username
gpr.token=your-github-token
```
