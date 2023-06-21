import {User_preview} from "./User_preview";
import {userData} from "./Chat";


function UserList({stateArray, clicker}) {
    var userArray=[]
    for(const property in stateArray) {
        userArray.push(<User_preview lastMsg={stateArray[property].lastMsg} time={stateArray[property].time} profilePic={stateArray[property].profilePic}
                                     displayName={stateArray[property].displayName} active={stateArray[property].active}
                                        click={()=>clicker(property)} key={property}/>);
    }
    return (<ul id="user-list">
        {userArray}
    </ul>)
}

export default UserList;