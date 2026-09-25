# instagram-backend-clone
instagram backend clone

## Stack

- Kotlin
- Spring Boot
- Spring Data JPA
- H2 in-memory database
- Spring Data MongoDB
- MongoDB via Docker Compose

## Run Locally

Use the `local` profile for H2 in-memory persistence. It does not require MongoDB.

```bash
SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```

The API runs on `http://localhost:8080`.

Data disappears when the server stops.

## Run with MongoDB

Use the `dev` profile when you want to persist data in MongoDB.

```bash
docker compose up -d
SPRING_PROFILES_ACTIVE=dev \
MONGODB_URI=mongodb://instagram:instagram@localhost:27017/instagram_clone?authSource=admin \
./gradlew bootRun
```

The API runs on `http://localhost:8080`.

`MONGODB_URI` is required in the `dev` profile.

## Deployment Practice

For a first deployment practice, use:

- Spring Boot server on AWS EC2
- MongoDB Atlas for the database
- systemd to keep the server running

See [deploy/ec2/README.md](deploy/ec2/README.md).

## API

### Create an image post

```bash
curl -X POST http://localhost:8080/api/posts \
  -H 'Content-Type: application/json' \
  -d '{
    "authorName": "uma",
    "imageUrl": "https://example.com/image.jpg",
    "caption": "first post"
  }'
```

### List posts

```bash
curl http://localhost:8080/api/posts
```

### Like a post

```bash
curl -X POST http://localhost:8080/api/posts/{postId}/likes \
  -H 'Content-Type: application/json' \
  -d '{"userName": "minji"}'
```

### Add a comment

```bash
curl -X POST http://localhost:8080/api/posts/{postId}/comments \
  -H 'Content-Type: application/json' \
  -d '{"authorName": "june", "content": "nice photo!"}'
```
