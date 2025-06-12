import "./InterfaceFooter.scss"

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
    faArrowRightToBracket,
    faBarsStaggered,
    faBookmark,
    faMagnifyingGlass,
    faSliders
} from "@fortawesome/free-solid-svg-icons";

export function InterfaceFooter({ onFilterClick}) {
    return (
        <div className="sonar-footer">
            <div className="sonar-footer_inner">

                <ul className="sonar-footer_menu">
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <FontAwesomeIcon icon={faMagnifyingGlass} />
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <FontAwesomeIcon icon={faBarsStaggered} />
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button
                            className="sonar-footer_menu-button"
                            onClick={onFilterClick}
                        >
                            <FontAwesomeIcon icon={faSliders} />
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <FontAwesomeIcon icon={faBookmark} />
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <FontAwesomeIcon icon={faArrowRightToBracket} />
                        </button>
                    </li>
                </ul>

            </div>
        </div>
    )
}