
import './App.css';
import Login from './login_signup/login/Login.js';
import React, {useState} from "react";
import Signup from './login_signup/signup/Signup';
import {BrowserRouter, Route, Routes, Navigate} from
        "react-router-dom";
import Chat from "./chat/Chat";


// real-time:
import io from 'socket.io-client';
const socket = io.connect("http://localhost:9000");


function App() {
    const [loggedIn, setLoggedIn] = useState(false);
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/login' element = {<Login setLoggedIn={setLoggedIn}/>} />
                <Route path='/' element={<Signup/>}/>
                { loggedIn ? (
                    <Route path='/chat' element={<Chat setLoggedIn={setLoggedIn}/>}/>
                ) : (
                    <Route path='/chat' element={<Navigate replace to="/" />} ></Route>
                )

                }
            </Routes>
        </BrowserRouter>

    );
}

export {App , socket};
