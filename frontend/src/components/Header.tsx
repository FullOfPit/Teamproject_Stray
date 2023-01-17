import "./Header.css";
import LogoSmall from "../Stray-Logo-Small.jpeg";
import {BsList, BsFillSkipEndFill} from 'react-icons/bs';
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";

export default function Header() {

    const [menuSideBarState, setMenuSideBarState] = useState<boolean>(false);
    const navigate = useNavigate();

    const redirectToSettings = () => {
        setMenuSideBarState(false);
        navigate("/settings", {replace: true})
    }

    return (
        <header className="header-bar">
            <div className="header-bar-container">
                <div className={"user-image-and-name-container"}>
                    <div className={"user-image-container"}>
                        <img className={"user-image"} src={LogoSmall} alt={"Error"}/>
                    </div>
                    <h4> Name - Go stray! </h4>
                </div>
                <div className={"menu-button-container"}>
                    <button onClick={() => setMenuSideBarState(!menuSideBarState)}>
                        <BsList size={32}/>
                    </button>
                </div>
            </div>
            {menuSideBarState &&
                <div className={"side-menu"}>
                    <div>
                        <div className={"side-menu-back"}> <BsFillSkipEndFill onClick={() => setMenuSideBarState(false)} size={25}/> </div>
                        <div className={"side-menu-menu"}><h4>Menu</h4></div>
                        <div className={"side-menu-option"}><h4>Create New Trip</h4></div>
                        <div className={"side-menu-option"}><h4>Future Features</h4></div>
                        <div className={"side-menu-settings"}><h4><Link  to={"/settings"} onClick={() => redirectToSettings()}>Settings</Link></h4></div>
                    </div>
                </div>
            }

        </header>
    )
}

//Swap Name for variable
//Swap Small logo for profile picture
//set useState for button
//link useState with main page