import "./InterfaceFooter.scss"
import React from "react";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {
    faArrowRightToBracket,
    faBarsStaggered,
    faBookmark,
    faMapLocationDot,
    faMagnifyingGlass,
    faArrowRightFromBracket
} from "@fortawesome/free-solid-svg-icons";
import {navigate} from "vike/client/router";
import {useCookies} from "react-cookie";

export function InterfaceFooter({currentPage}) {
    const [cookies, setCookie, removeCookie] = useCookies(['email']);
    console.log(cookies.eamil);
    let loggedIn = (cookies.email !== (undefined));
    console.log(loggedIn);
    async function navigateToFilter() {
        await navigate('/filter');
    }

    async function navigateToInteractiveMap() {
        await navigate('/interactivemap');
    }

    async function navigateToList() {
        await navigate('/list');
    }

    async function navigateToBookmarks() {
        await navigate('/bookmarks');
    }

    async function navigateToProfile() {
        await navigate('/profile');
    }

    return (
        <div className="sonar-footer">
            <div className="sonar-footer_inner">
                <ul className="sonar-footer_menu">
                    <li className="sonar-footer_menu-item">
                        <button
                            id="filter"
                            className={currentPage !== "/filter" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                            onClick={navigateToFilter}
                        >
                            <FontAwesomeIcon icon={faMagnifyingGlass}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="interactivemap" className={currentPage !== "/interactivemap" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToInteractiveMap}
                        >
                            <FontAwesomeIcon icon={faMapLocationDot}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="list" className={currentPage !== "/list" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToList}
                        >
                            <FontAwesomeIcon icon={faBarsStaggered}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="bookmark" className={currentPage !== "/bookmarks" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToBookmarks}
                        >
                            <FontAwesomeIcon icon={faBookmark}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="account" className={currentPage !== "/account" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"} onClick={navigateToProfile}>
                            {loggedIn ? <FontAwesomeIcon icon={faArrowRightFromBracket}/> : <FontAwesomeIcon icon={faArrowRightToBracket}/>}
                        </button>
                    </li>
                </ul>

            </div>
        </div>
            )
}