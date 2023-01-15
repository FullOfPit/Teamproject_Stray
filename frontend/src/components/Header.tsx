import "./Header.css";
import LogoSmall from "../Stray-Logo-Small.jpeg";
import {BiMenu} from 'react-icons/bi';
import {useState} from "react";

export default function Header() {

    const [menuSideBarState, setMenuSideBarState] = useState<boolean>(false);

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
                        <BiMenu/>
                    </button>
                </div>
            </div>
        </header>
    )
}

//Swap Name for variable
//Swap Small logo for profile picture
//set useState for button
//link useState with main page