import logo from "../../../assets/images/logo.png";
import "./InterfaceHeader.scss"
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faGlobe} from "@fortawesome/free-solid-svg-icons";

export function InterfaceHeader() {
    return (
        <div className="sonar-header">
            <div className="sonar-header_inner">

                <div className="sonar-header_logo">
                    <img className="sonar-header_logo-image" src={logo} alt={"Wappen der Stadt Herne"}/>
                    <p className="sonar-header_logo-text">SONAR</p>
                </div>

                <div className="sonar-header_lang">
                    <label className="sonar-header_dropdown-label" htmlFor="sonar-header_lang-select">
                        <FontAwesomeIcon icon={faGlobe} />
                    </label>
                    <select className="sonar-header_dropdown" id="sonar-header_lang-select">
                        <option value="DE">DE</option>
                        <option value="EN">EN</option>
                    </select>
                </div>

            </div>
        </div>
    )
}