// css
import '../css/chat_main.css';
import '../css/chat_user.css';
import '../css/chat_window.css';

// // javascript
import {Chat_window} from "./Chat_window.js";
import {useEffect, useRef, useState} from "react";
import UserList from "./UserList"
import {useNavigate} from 'react-router-dom'
import {token} from '../login_signup/login/Login';

import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import {socket} from '../App.js';

export var name_picture = {
    _displayname:"",
    _profilePicture:"",
    _userName:"",
    set displayname(value) {
        this._displayname = value;
    },
    get displayname() {
        return this._displayname;
    },
    set profilePicture(value) {
        this._profilePicture = value;
    },
    get profilePicture() {
        return this._profilePicture;
    },
    set userName(value) {
        this._userName = value;
    },
    get userName() {
        return this._userName;
    }
}

let activeUser = null;
let activeId = null;
function Chat({setLoggedIn}) {
    const [currentUser, setCurrentUser] = useState("");
    const [currentMessages, setCurrentMessages] = useState([]);
    const inputRef = useRef("");
    const [addUserErrorText, setAddUserErrorText] = useState("");
    const [show, setShow] = useState(false);
    const [invalid, setInvalid] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    //const [stateArray, setStateArray] = useState({"Daniel":{time:1, lastMsg:"prrr", active:"active"}});

    const [stateArray, setStateArray] = useState(null); // user list at left side of screen.
    useEffect(()=> { // when component mounts
        activeUser=null;
        activeId=null;
        getUserChatsFromServer().then(setStateArray);
    },[])
    const addRef = useRef("")
    const navigate = useNavigate();

    useEffect(() => {
        socket.on("receive_message", (data) => {
            console.log(activeUser);
            console.log(activeId);
            getUserChatsFromServer().then(setStateArray);
            if (activeId !== null) {
                upd();
            }
        })
    }, [socket]);

    function currTime() {
        var today = new Date();
        return today.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
    }
    function adaptFormat(obj) {
        // change the object given from server from chats, to syntax needed to stateArray
        var ObjectToStateArray = {};
        for(const user of obj) {
            var LASTMSGID;
            var TIME;
            var CONTENT;
            if(!user.lastMessage) {
                LASTMSGID=-1;
                TIME="";
                CONTENT="";
            } else {
                LASTMSGID=user.lastMessage.id;
                TIME=user.lastMessage.created;
                const datetime = new Date(TIME);
                // Extracting the time
                const time = datetime.toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });

                // Extracting the date
                const date = datetime.toLocaleDateString();
                TIME = time + " " + date;
                CONTENT=user.lastMessage.content;
            }
            if (user.user.username !== activeUser) {
                ObjectToStateArray[user.user.username] = {
                    displayName: user.user.displayName, profilePic: user.user.profilePic, lastMsg: CONTENT,
                    lastMsgId: LASTMSGID, time: TIME, active: "inactive", chatId: user.id
                }
            } else {
                ObjectToStateArray[user.user.username] = {
                    displayName: user.user.displayName, profilePic: user.user.profilePic, lastMsg: CONTENT,
                    lastMsgId: LASTMSGID, time: TIME, active: "active", chatId: user.id
                }
            }
        }
        return ObjectToStateArray;
    }
    async function getUserChatsFromServer() {
        const res = await fetch('http://localhost:5000/api/Chats', {
            'method':'get',
            'headers' : {
                'Content-Type': 'application/json',
                'authorization' : "bearer " + token,
            }
        });
        var j = await res.json();
        return adaptFormat(j);
    }
    async function SendMsg() {
        var msg = inputRef.current.value.trim();
        if (msg === ""){
            return;
        }
        // show the message on screen
        setCurrentMessages([...currentMessages, {content:msg,sender:"You"}])
        const data = {
            msg:msg
        }
        const id = stateArray[currentUser].chatId.toString();
        const res = await fetch('http://localhost:5000/api/Chats/'+id+'/Messages', {
            'method':'post',
            'headers':{
                'Content-Type': 'application/json',
                'authorization' : "bearer " + token,
            },
            'body':JSON.stringify(data)
        })
        setStateArray({...stateArray, [currentUser]: {...stateArray[currentUser], lastMsg:msg, time:currTime()}})
        inputRef.current.value="";
        socket.emit("send_message", {chat_id: id});

    }
    // return false


    async function AddUser() {
        var user = addRef.current.value // username
        if(user in stateArray){
            setInvalid(true);
            setAddUserErrorText("Can't chat with user more than once.")
            return;
        }
        const data = {
            username:user
        }
        const res2 = await fetch('http://localhost:5000/api/Chats', {
            'method':'post',
            'headers' : {
                'Content-Type': 'application/json',
                'authorization' : "bearer " + token,
            },
            'body' : JSON.stringify(data)
        });
        console.log(res2.status);
        if(res2.status!==200) {
            setInvalid(true);
            setAddUserErrorText("Invalid user to add.")
            return;
        }
        const json2 = await res2.json(); // result should have the info on the requested user in data.
        /*
        * The response looks like this:
        * {
          "id": 2,
          "user": {
            "username": "bob",
            "displayName": "BOB",
            "profilePic": "string"
          }
            }
            * */
        var dictToAdd = {
            time: "", lastMsg: "", active: "active",displayName:json2.user.displayName,
            profilePic:json2.user.profilePic,lastMsgId:-1,chatId:json2.id
        }
        if(currentUser!=="") {
            setStateArray({
                ...stateArray, [currentUser]: {...stateArray[currentUser], active: "inactive"},
                [user]: dictToAdd
            });
        } else {
            setStateArray({
                ...stateArray,
                [user]: dictToAdd
            });
        }
        setCurrentMessages(oldArray=>[]);
        setCurrentUser(user);
        setShow(false);
        activeId = dictToAdd.chatId;
        activeUser = user;
        socket.emit("send_message", {chat_id: 0});
    }
    async function upd(){
        const res = await fetch('http://localhost:5000/api/Chats/' + activeId + '/Messages', {
            'method': 'get',
            'headers': {
                'Content-Type': 'application/json',
                'authorization': "bearer " + token,
            }
        })
        const messagesNew = [];
        const messageList = await res.json();
        const reversedMessageList = messageList.reverse();
        for (const message of reversedMessageList) { // reverse because of the way the server saves the messages
            var sender = ""
            if (message.sender.username === name_picture.userName) {
                sender = "You"
            } else {
                sender = "Other"
            }
            messagesNew.push({content: message.content, sender: sender})
        }
        setCurrentMessages(oldArray => messagesNew);
    }
    async function switchUsers(username, update = 0, _stateArray = null) {
        activeUser = username;
        let res;
        if (update === 1) {
            res = await fetch('http://localhost:5000/api/Chats/' + activeId + '/Messages', {
                'method': 'get',
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': "bearer " + token,
                }
            })
        } else {
            if (currentUser !== "") {
                setStateArray({
                    ...stateArray, [currentUser]: {...stateArray[currentUser], active: "inactive"},
                    [username]: {...stateArray[username], active: "active"}
                });
            } else {
                setStateArray({
                    ...stateArray,
                    [username]: {...stateArray[username], active: "active"}
                });
            }
            const id = stateArray[username].chatId.toString();
            activeId = id;
            res = await fetch('http://localhost:5000/api/Chats/' + id + '/Messages', {
                'method': 'get',
                'headers': {
                    'Content-Type': 'application/json',
                    'authorization': "bearer " + token,
                }
            })

        }
        const messagesNew = [];
        const messageList = await res.json();
        const reversedMessageList = messageList.reverse();
        for (const message of reversedMessageList) { // reverse because of the way the server saves the messages
            var sender = ""
            if (message.sender.username === name_picture.userName) {
                sender = "You"
            } else {
                sender = "Other"
            }
            messagesNew.push({content: message.content, sender: sender})
        }
        setCurrentMessages(oldArray => messagesNew);
        setCurrentUser(username);
    }

    async function keydownSendMsg(event){
        if(event.key === "Enter") {
            event.preventDefault();
            await SendMsg();
        }
    }
    async function keydownAddUser(event) {
        if(event.key === "Enter") {
            event.preventDefault();
            await AddUser();
        }
    }
    function logOut() {
        setLoggedIn(false);
        navigate('/login',{replace:true});
    }
    return (
        <>
            <title>Chat</title>
            <link href="../css/chat_main.css" rel="stylesheet"/>
            <link href="../css/chat_user.css" rel="stylesheet"/>
            <link href="../css/chat_window.css" rel="stylesheet"/>
            <div className="chat-container">
                <div className="user-list-container">
                    <div className="profile-container">
                        <div className="profile-picture">
                            <img
                                src={`data:image/jpeg;charset=utf-8;base64,${name_picture.profilePicture}`}
                                className="rounded-circle" alt="Profile Picture"/>
                        </div>

                        <div className="profile-username">
                            {name_picture.displayname}
                        </div>

                        <div className="add-user" onClick={handleShow}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 className="bi bi-person-add" viewBox="0 0 16 16">
                                <path
                                    d="M12.5 16a3.5 3.5 0 1 0 0-7 3.5 3.5 0 0 0 0 7Zm.5-5v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1 0-1h1v-1a.5.5 0 0 1 1 0Zm-2-6a3 3 0 1 1-6 0 3 3 0 0 1 6 0ZM8 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4Z"/>
                                <path
                                    d="M8.256 14a4.474 4.474 0 0 1-.229-1.004H3c.001-.246.154-.986.832-1.664C4.484 10.68 5.711 10 8 10c.26 0 .507.009.74.025.226-.341.496-.65.804-.918C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4s1 1 1 1h5.256Z"/>
                            </svg>
                        </div>
                    </div>
                    <h2>Users</h2>
                    <UserList stateArray={stateArray} clicker={switchUsers}/>
                </div>

                <button className="btn btn-danger log-out-button" onClick={logOut}>Log out</button>

                <div className="chat-window-container">
                    <div className="chat-header">
                        <div className="user-container">
                            <div className="chat-profile-picture">
                            </div>
                            <div className="friend-username">
                            </div>
                        </div>
                    </div>
                    <Chat_window  messagesContent={currentMessages}
                    ></Chat_window>
                    {(currentUser!=="") && (<form id="chat-area">
                        <input ref={inputRef} rows="1" name="text" id="messageBar"
                               placeholder="Type your message here..." onKeyDown={keydownSendMsg} />
                        <button type="button" className="btn btn-primary" id="msg" onClick={SendMsg}>Send</button>
                    </form>)}



                </div>
            </div>
            <Modal show={show} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add User</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <Form.Group className="mb-3">
                            <Form.Label htmlFor="recipient-name" className="col-form-label">Contact name:</Form.Label>
                            <Form.Group className="input-group">
                                <div className="input-group-text">@</div>
                                <Form.Control type="text" className="form-control" id="recipient-name" ref={addRef}
                                              isInvalid = {invalid} onKeyDown={keydownAddUser} />
                                <Form.Control.Feedback type="invalid">{addUserErrorText}</Form.Control.Feedback>
                            </Form.Group>
                        </Form.Group>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={AddUser}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </>

    );
}

export default Chat;
