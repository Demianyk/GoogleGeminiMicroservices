# Dockerized microservices for interacting with Google Gemini API via Telegram Bot.
Dockerized microservices for interacting with Google Gemini API via Telegram Bot.
## Building
```bash
docker-compose build
```

## Dockerized microservices mode

### Running
Set env variables TELEGRAM_BOT_TOKEN and GOOGLE_API_KEY and run
```bash
docker-compose up
```

### Stopping
```bash
docker-compose stop
```

### Stopping and removing containers
```bash
docker-compose down 
```
### Stopping and removing containers and wiping everything  
```bash
docker-compose down --volumes --rmi all
```

## Dockerized monolith mode
### Building image
```bash
docker build --file .\Dockerfile-monolith -t ai-agent-monolith .
```

### Running
```bash
docker run -e TELEGRAM_BOT_TOKEN="<token>" -e GOOGLE_API_KEY="<API key>" ai-agent-monolith
```

## License

This project is licensed under the MIT License.
