import "./InterfaceFooter.scss"

import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {
    faArrowRightToBracket,
    faBarsStaggered,
    faBookmark,
    faMagnifyingGlass,
    faSliders
} from "@fortawesome/free-solid-svg-icons";
import {navigate} from "vike/client/router";

export function InterfaceFooter() {


    async function navigateToFilter() {
        await navigate('/filter');
    }

    async function navigateToSearch() {
        await navigate('/search');
    }

    async function navigateToList() {
        await navigate('/list');
    }

    async function navigateToBookmarks() {
        await navigate('/bookmarks');
    }

    return (
        <div className="sonar-footer">
            <div className="sonar-footer_inner">

                <ul className="sonar-footer_menu">
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button"
                                onClick={navigateToSearch}
                        >
                            <FontAwesomeIcon icon={faMagnifyingGlass}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button"
                                onClick={navigateToList}
                        >
                            <FontAwesomeIcon icon={faBarsStaggered}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button
                            className="sonar-footer_menu-button"
                            onClick={navigateToFilter}
                        >
                            <FontAwesomeIcon icon={faSliders}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button"
                                onClick={navigateToBookmarks}
                        >
                            <FontAwesomeIcon icon={faBookmark}/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <FontAwesomeIcon icon={faArrowRightToBracket}/>
                        </button>
                    </li>
                </ul>

            </div>
        </div>
    )
}