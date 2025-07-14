import "./InterfaceFooter.scss"
import React from "react";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {
    faArrowRightToBracket,
    faBarsStaggered,
    faBookmark,
    faMagnifyingGlass,
    faMapLocationDot
} from "@fortawesome/free-solid-svg-icons";
import {navigate} from "vike/client/router";

export function InterfaceFooter({pageContext}) {
    let currentPage = pageContext.urlPathname;
    let highlightFilter = currentPage == "/list" && pageContext.urlOriginal != "/list";
    let highlightList = currentPage == "/list" && !highlightFilter;

    async function navigateToFilter() {
        await navigate('/filter');
    }

    async function navigateToInteractiveMap() {
        await navigate('/interactivemap');
    }

    async function navigateToList() {
        await navigate('/');
    }

    async function navigateToBookmarks() {
        if (localStorage.getItem("email") === null) {
           await navigate("/profile");
        }
        await navigate('/bookmarks');
    }

    async function navigateToProfile() {
        if (localStorage.getItem("email") !== null) {
            localStorage.removeItem("email");
            localStorage.removeItem("token");
            alert("Logout erfolgreich!");
            await navigate("/")
        } else {
            await navigate('/profile');
        }
    }

    return (
        <div className="sonar-footer">
            <div className="sonar-footer_inner">
                <ul className="sonar-footer_menu">
                    <li className="sonar-footer_menu-item">
                        <button
                            id="filter"
                            className={highlightFilter ? "sonar-footer_menu-button active" : "sonar-footer_menu-button"}
                            onClick={navigateToFilter}
                        >
                            <FontAwesomeIcon icon={faMagnifyingGlass}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="interactivemap"
                                className={currentPage !== "/interactivemap" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToInteractiveMap}
                        >
                            <FontAwesomeIcon icon={faMapLocationDot}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="list"
                                className={highlightList ? "sonar-footer_menu-button active" : "sonar-footer_menu-button"}
                                onClick={navigateToList}
                        >
                            <FontAwesomeIcon icon={faBarsStaggered}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="bookmark"
                                className={currentPage !== "/bookmarks" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToBookmarks}
                        >
                            <FontAwesomeIcon icon={faBookmark}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button id="account"
                                className={currentPage !== "/account" ? "sonar-footer_menu-button" : "sonar-footer_menu-button active"}
                                onClick={navigateToProfile}>
                            <FontAwesomeIcon icon={faArrowRightToBracket}/>
                        </button>
                    </li>
                </ul>

            </div>
        </div>
    )
}