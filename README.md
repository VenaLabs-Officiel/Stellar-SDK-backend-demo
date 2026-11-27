# Venalabs Backend SDK

Reference Spring Boot application showing how to generate access tokens for the [VenaLabs Stellar SDK](https://github.com/VenaLabs-Officiel/Stellar-SDK) (`@venalabs/stellar-sdk`, version 2.1.0 or later).

Your backend authenticates with the Venalabs API using your API credentials and returns a user access token (valid for 24 hours) that your frontend passes to the SDK. See the [integration guide](https://github.com/VenaLabs-Officiel/Stellar-SDK/blob/main/docs/integration-guide.md) for the full flow.

## How It Works

```
Frontend                     Your Backend                  Venalabs API
   │                              │                              │
   │  GET /api/venalabs-token     │                              │
   │ ──────────────────────────►  │                              │
   │                              │  POST /api/v1/sdk/auth/token │
   │                              │  X-Api-Key / X-Api-Secret    │
   │                              │ ────────────────────────────► │
   │                              │                              │
   │                              │  { "token", "expiresIn" }    │
   │                              │ ◄──────────────────────────── │
   │  { "token": "eyJ..." }       │                              │
   │ ◄──────────────────────────  │                              │
```

> **Important:** API credentials (`apiSecret`) must stay on your server. Never expose them to the client.

## Prerequisites

- Java 22+
- Maven 3.x

## Quick Start

### 1. Configure your credentials

Get your API key and secret from the [VenaLabs dashboard](https://venalabs.com/en/saas/admin/sdk-integration), then set them as environment variables:

```bash
export VENALABS_API_KEY=your-api-key
export VENALABS_API_SECRET=your-api-secret
```

Or edit `src/main/resources/application.yml` directly:

```yaml
venalabs:
  api-key: your-api-key
  api-secret: your-api-secret
```

### 2. Run the server

```bash
mvn spring-boot:run
```

The server starts on **port 8081**.

### 3. Get a token

```bash
curl http://localhost:8081/api/venalabs-token
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

## Integrate in Your App

This demo uses a hardcoded user ID. In a real application, extract the authenticated user's ID from your session or JWT and pass it when requesting a token:

```java
@GetMapping("/api/venalabs-token")
public ResponseEntity<Map<String, String>> getVenalabsToken(@AuthenticationPrincipal User user) {
    String accessToken = venalabsTokenService.getAccessToken(user.getId());
    return ResponseEntity.ok(Map.of("token", accessToken));
}
```

## Project Structure

```
src/main/java/com/venalabs/demo/
├── VenalabsBackendSdkApplication.java   # Entry point
├── config/
│   ├── VenalabsProperties.java          # venalabs.* config binding
│   └── RestTemplateConfig.java          # HTTP client bean
├── controller/
│   └── VenalabsTokenController.java     # GET /api/venalabs-token
└── service/
    └── VenalabsTokenService.java        # Calls Venalabs API
```

## Configuration Reference

| Property | Env Variable | Default | Description |
|---|---|---|---|
| `venalabs.api-url` | `VENALABS_API_URL` | `https://api.venalabs.com` | Venalabs API base URL |
| `venalabs.api-key` | `VENALABS_API_KEY` | - | Your API key |
| `venalabs.api-secret` | `VENALABS_API_SECRET` | - | Your API secret |
| `server.port` | `SERVER_PORT` | `8081` | Server port |

## Frontend

The matching React application is [Stellar-SDK-frontend-demo](https://github.com/VenaLabs-Officiel/Stellar-SDK-frontend-demo). It reads `data.token` from this endpoint.

## License

MIT, see [LICENSE](./LICENSE).
