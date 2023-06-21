function Message({content, messenger}){
    if (messenger === "You") {
        return (<div className="message my-message me">
                    <div className="message-text me"> {content} </div>
                </div>);
    } else {
        return (<div className="message other-message other">
                    <div className="message-text other"> {content} </div>
                </div> );
    }
}
export {Message};

