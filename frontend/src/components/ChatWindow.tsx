import {useState} from "react";
import {type AiResponse} from "../model/AiReponse";
import {deleteApi, postApi} from "../hooks/api-hooks";

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || "";


function ChatWindow() {
    const [chatHistory, setChatHistory] = useState([]);
    const [userInput, setUserInput] = useState("");
    const handleSend = () => {
        if (userInput.trim()) {
            setChatHistory(prev => [...prev, {message: userInput, sender: "User"}]);
            postApi<AiResponse>('/api/ai/send-message', {message: userInput})
                .then(data => {
                    setChatHistory(prev => [...prev, {message: data.message, sender: "AI"}])
                })
            setUserInput("");
        }
    };

    const handleClearHistory = () => {
        deleteApi('/api/ai/clear-history')
            .then(() => setChatHistory([]));
    };

    return (
        <div style={{padding: 20, border: "1px solid #ccc", borderRadius: 5, width: 400}}>
            <h2>Chat Window</h2>
            <div style={{
                height: 200,
                overflowY: "auto",
                border: "1px solid #ddd",
                padding: 10,
                marginBottom: 10
            }}>
                {chatHistory.map((chat, index) => (
                    <div key={index} style={{marginBottom: 5}}>
                        <strong>{chat.sender}:</strong> {chat.message}
                    </div>
                ))}
            </div>
            <textarea
                value={userInput}
                onChange={(e) => setUserInput(e.target.value)}
                rows={3}
                style={{width: "100%", marginBottom: 10}}
            />
            <button onClick={handleSend} style={{width: "100%", marginBottom: 10}}>Send</button>
            <button onClick={handleClearHistory} style={{width: "100%"}}>Clear History</button>
        </div>
    );
}

export default ChatWindow;
