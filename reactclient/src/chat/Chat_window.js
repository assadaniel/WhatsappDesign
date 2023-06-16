import {Message} from "./Message.js";
function Chat_window({messagesContent}){
    var messages = messagesContent.map((content, key) =>{
        return <Message content={content.content} messenger={content.sender} key={key}></Message>;
    });
    return (<div id="chat-window">
        {messages}
    </div>);
}
export {Chat_window};
