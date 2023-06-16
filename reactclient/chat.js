// css
import './chat/css/chat_main.css';
import './chat/css/chat_user.css';
import './chat/css/chat_window.css';
// // javascript
import './chat/javascript/chat_screen.js';
import './chat/javascript/send_message.js';
import {loadBar, loadButton, keyUp} from "./chat/javascript/send_message.js";
import {loadList} from "./chat/javascript/chat_screen";


function Chat() {

    return (
        <>
            <title>Chat</title>
            <link href="./chat/css/chat_main.css" rel="stylesheet"/>
            <link href="./chat/css/chat_user.css" rel="stylesheet"/>
            <link href="./chat/css/chat_window.css" rel="stylesheet"/>
            <div className="chat-container">
                <div className="user-list-container">
                    <div className="profile-container">
                        <div className="profile-picture">
                            <img
                                src="https://www.clipartmax.com/png/middle/191-1919133_we-good-profile-picture-for-steam.png"
                                className="rounded-circle" alt="Profile Picture"/>
                        </div>

                        <div className="profile-username">
                            Tester
                        </div>

                        <div className="add-user" data-bs-toggle="modal" data-bs-target="#exampleModal">
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
                    <ul id="user-list" onLoad={loadList}>
                        <li className="inactive">
                            <div className="user-container">
                                <div className="profile-picture">
                                    <img src="https://live.staticflickr.com/65535/52793922741_9b9af08988_z.jpg"
                                         className="rounded-circle" alt="Daniel L Picture"/>
                                </div>

                                <div className="friend-username">
                                    Daniel L
                                </div>
                                <div className="user-last">
            <span className="user-time">

            </span>
                                    <br/>
                                    <span className="user-last-message">

            </span>
                                </div>
                            </div>

                        </li>
                        <li className="inactive">
                            <div className="user-container">
                                <div className="profile-picture">
                                    <img src="https://live.staticflickr.com/65535/52794315375_2c6a61365a_z.jpg"
                                         className="rounded-circle" alt="Daniel A Picture"/>
                                </div>

                                <div className="friend-username">
                                    Daniel A
                                </div>
                                <div className="user-last">
            <span className="user-time">

            </span>
                                    <br/>
                                    <span className="user-last-message">

            </span>
                                </div>
                            </div>
                        </li>
                        <li className="inactive">
                            <div className="user-container">
                                <div className="profile-picture">
                                    <img src="https://live.staticflickr.com/65535/52794315345_87fd41c855_z.jpg"
                                         className="rounded-circle" alt="Nitai D Picture"/>
                                </div>
                                <div className="friend-username">
                                    Nitai D
                                </div>
                                <div className="user-last">
            <span className="user-time">

            </span>
                                    <br/>
                                    <span className="user-last-message">

            </span>
                                </div>
                            </div>
                        </li>
                        <script type="module" src="src/chat/javascript/chat_screen.js"></script>
                    </ul>
                </div>
                <button className="btn btn-danger log-out-button">Log out</button>
                <div className="chat-window-container">
                    <div className="chat-header">
                        <div className="user-container">
                            <div className="chat-profile-picture">
                            </div>
                            <div className="friend-username">
                            </div>
                        </div>
                    </div>
                    <div id="chat-window" className="default">
                        <div className="message other-message">
                            <div className="message-text default">Please open a chat:)</div>
                        </div>
                        <div className="message my-message">
                            <div className="message-text default">This is a placeholder for the default page</div>
                        </div>
                    </div>
                    <form id="chat-area">
                        <textarea rows="1" name="text" id="messageBar"
                                  placeholder="Type your message here..." onKeyDownCapture={loadBar} onKeyUp={keyUp}></textarea>
                        <button type="button" className="btn btn-primary" id="msg" onClick={loadButton}>Send</button>
                        <script type="module" src="src/chat/javascript/send_message.js"></script>
                    </form>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
                            crossOrigin="anonymous"></script>
                </div>
            </div>
            <div className="modal fade" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
                 aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">New contact</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                            <form>
                                <div className="mb-3">
                                    <label htmlFor="recipient-name" className="col-form-label">Contact name:</label>
                                    <div className="input-group">
                                        <div className="input-group-text">@</div>
                                        <input type="text" className="form-control" id="recipient-name"/>
                                    </div>
                                </div>
                                <div className="modal-footer">
                                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close
                                    </button>
                                    <button type="submit" className="btn btn-primary" data-bs-dismiss="modal">Add
                                        contact
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </>

    );
}
export default Chat;
