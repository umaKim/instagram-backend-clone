# EC2 Deployment Practice

This guide deploys the Spring Boot API to one Ubuntu EC2 instance and uses MongoDB Atlas as the database.

The EC2 deployment uses the `dev` profile because it connects to MongoDB. The `local` profile is only for H2 in-memory development.

## 1. Prepare MongoDB Atlas

1. Create an Atlas cluster.
2. Create a database user.
3. In Network Access, allow the EC2 public IP as `/32`.
4. Copy the application connection string.

Example:

```text
mongodb+srv://<username>:<password>@<cluster-url>/instagram_clone?retryWrites=true&w=majority
```

## 2. Create EC2

Recommended for practice:

- Ubuntu 24.04 LTS
- t3.micro or t2.micro
- Security Group inbound rules:
  - SSH `22` from your IP
  - HTTP `8080` from your IP for practice

For a real service, put Nginx or an AWS Load Balancer in front and expose `443` instead of `8080`.

## 3. Install Java and Git on EC2

```bash
sudo apt update
sudo apt install -y openjdk-17-jre-headless git
java -version
```

## 4. Build locally

On your Mac:

```bash
./gradlew clean bootJar
```

Copy the jar to EC2:

```bash
scp -i <key.pem> build/libs/*.jar ubuntu@<ec2-public-ip>:/tmp/app.jar
```

## 5. Configure the app on EC2

SSH into EC2:

```bash
ssh -i <key.pem> ubuntu@<ec2-public-ip>
```

Create the app directory:

```bash
sudo mkdir -p /opt/instagram-backend-clone
sudo mv /tmp/app.jar /opt/instagram-backend-clone/app.jar
sudo chown -R ubuntu:ubuntu /opt/instagram-backend-clone
```

Create the environment file:

```bash
sudo nano /etc/instagram-backend-clone.env
```

Paste:

```text
SPRING_PROFILES_ACTIVE=dev
PORT=8080
MONGODB_URI=mongodb+srv://<username>:<password>@<cluster-url>/instagram_clone?retryWrites=true&w=majority
```

## 6. Run with systemd

Copy the service file:

```bash
sudo nano /etc/systemd/system/instagram-backend.service
```

Paste the contents of `deploy/ec2/instagram-backend.service.example`.

Start the service:

```bash
sudo systemctl daemon-reload
sudo systemctl enable instagram-backend
sudo systemctl start instagram-backend
sudo systemctl status instagram-backend
```

Check logs:

```bash
journalctl -u instagram-backend -f
```

## 7. Test

From your Mac:

```bash
curl http://<ec2-public-ip>:8080/api/posts
```

Create a post:

```bash
curl -X POST http://<ec2-public-ip>:8080/api/posts \
  -H 'Content-Type: application/json' \
  -d '{
    "authorName": "uma",
    "imageUrl": "https://example.com/image.jpg",
    "caption": "deployed post"
  }'
```

## Update Deployment

Build and copy a new jar:

```bash
./gradlew clean bootJar
scp -i <key.pem> build/libs/*.jar ubuntu@<ec2-public-ip>:/tmp/app.jar
```

On EC2:

```bash
sudo systemctl stop instagram-backend
sudo mv /tmp/app.jar /opt/instagram-backend-clone/app.jar
sudo chown ubuntu:ubuntu /opt/instagram-backend-clone/app.jar
sudo systemctl start instagram-backend
```
