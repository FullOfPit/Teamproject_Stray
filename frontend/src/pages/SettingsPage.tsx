import "./SettingsPage.css"
import LogoSmall from "../Stray-Logo-Small.jpeg";
import {useNavigate} from "react-router-dom";

export default function SettingsPage() {

    const navigate = useNavigate();

    return (

        <div className={"settings-main"}>
            <div className={"settings-user-photo"}>
                <img src={LogoSmall} alt={"Something went wrong :("}/>
                <h3>Settings</h3>
            </div>
            <div className={"settings-content"}>
                <div className={"settings-user-name"}>
                    <h4>{"User Name"}</h4>
                    <button>Edit</button>
                </div>
                <div className={"settings-user-password"}>
                    <h4>Change password</h4>
                </div>
            </div>

            <div className={"settings-redirect"}>
                <h4 onClick={() => navigate("/", {replace:true})}>Stray back to the dashboard</h4>
            </div>
        </div>
    );
}
//header needs to be removed afterwards