import {useEffect, useState} from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";


const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || "";
const loginBaseUrl = API_BASE_URL;

function LoginPage() {
    const handleLogin = () => {
        // Redirect to Spring Boot OAuth2 login endpoint
        window.location.href = loginBaseUrl + "/oauth2/authorization/google";
    };

    return (
        <div style={{padding: 20}}>
            <h1>Login Page</h1>
            <button onClick={handleLogin}>Login with Google</button>
        </div>
    );
}

function Spa() {
    const [userInfo, setUserInfo] = useState(null);

    useEffect(() => {
        fetch(API_BASE_URL + "/api/userinfo", {
            credentials: "include", // important to send session cookie
        })
            .then((res) => {
                if (!res.ok) throw new Error("Not authenticated");
                return res.json();
            })
            .then(setUserInfo)
            .catch(() => setUserInfo(null));
    }, []);
    const linkToRoot = () => {
        window.location.href = "/";
    };
    const redirectToRootButton = (
        <button onClick={linkToRoot} style={{marginBottom: 20}}>
            Back to Login
        </button>
    );
    if (!userInfo) return <>
        {redirectToRootButton}
        <div>Loading user info or not logged in...</div>
        ;
    </>

    return (
        <>
            {redirectToRootButton}
            <div style={{padding: 20}}>
                <p>Email: {userInfo.email}</p>
            </div>
        </>

    );
}

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/spa" element={<Spa/>}/>
            </Routes>
        </BrowserRouter>
    );
}