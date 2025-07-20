# Dockerized microservices for interacting with Google Gemini API via Telegram Bot.
Dockerized microservices for interacting with Google Gemini API via Telegram Bot.
## Building
```bash
docker-compose build
```

## Dockerized microservices mode

### Running
Set env variables TELEGRAM_BOT_TOKEN, GOOGLE_API_KEY, GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET and run
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
docker run -e TELEGRAM_BOT_TOKEN="<token>" -e GOOGLE_API_KEY="<API key>" -e GOOGLE_CLIENT_ID="<oauth client id>" -e GOOGLE_CLIENT_SECRET="<oauth client secret>" -p 8080:8080 ai-agent-monolith
```

## Clearing chat context
The chat context is stored in memory. To clear it, write `/clear` in the chat with the bot. If not cleared, the context will be retained for the next interaction. It may lead to growing context window and increased response time. Also, it may lead to memory issues if the context grows too large. 

## License

This project is licensed under the MIT License.
