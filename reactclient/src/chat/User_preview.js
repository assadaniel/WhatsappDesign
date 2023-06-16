function User_preview({lastMsg, displayName, time, profilePic, active, click}){
    //console.log(username);
    //let altName = {username} + "Profile picture";
    let altName = "Profile Picture"
    var strToDisplay=""
    if(profilePic==="https://images-na.ssl-images-amazon.com/images/I/51e6kpkyuIL._AC_SX466_.jpg"){
        strToDisplay=profilePic
    } else {
        strToDisplay = `data:image/jpeg;charset=utf-8;base64,${profilePic}`
    }
    return (<li className={active} onClick={click}>
        <div className="user-container">
            <div className="profile-picture">
                <img src={strToDisplay}
                     className="rounded-circle" alt={altName}/>
            </div>
            <div className="friend-username">
                {displayName}
            </div>
            <div className="user-last">
                <span className="user-time">
                    {time}
                </span>
                <br/>
                <span className="user-last-message">
                    {lastMsg}
                </span>
            </div>
        </div>
    </li>)
}
export {User_preview};