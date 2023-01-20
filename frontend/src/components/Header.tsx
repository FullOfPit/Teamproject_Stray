import "./Header.css";
import LogoSmall from "../Stray-Logo-Small.jpeg";
import {BsList, BsFillSkipEndFill} from 'react-icons/bs';
import {useState} from "react";
import {Link, useNavigate} from "react-router-dom";

export default function Header() {

    const [menuSideBarState, setMenuSideBarState] = useState<boolean>(false);
    const navigate = useNavigate();

    const redirect = () => {
        setMenuSideBarState(false);
        navigate("/trips/create", {replace: true})
    }


    return (
        <header className="header-bar">
            <div className="header-bar-container">
                <div className={"user-image-and-name-container"}>
                    <div className={"user-image-container"}>
                        <Link to={"/"}><img className={"user-image"} src={LogoSmall} alt={"Error"}/></Link>
                    </div>
                    <h4> Stray - find your way! </h4>
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
                        <div className={"side-menu-option"}><Link to={"/trips/create"} onClick={redirect}><h4>Create New Trip</h4> </Link></div>
                    </div>
                </div>
            }

        </header>
    )
}