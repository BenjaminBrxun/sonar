import logo from "../assets/logo.png";
import LanguageIcon from '@mui/icons-material/Language';
import './Style.css'

export function TitleBar() {
    return (
        <div className="title-bar">
            <img className="sonar-logo" src={logo} alt={"Wappen der Stadt Herne"}/>
            <p className="title">HERNEBUZZ</p>
            <div className="lan-selection">
                <LanguageIcon className="icon"/>
                <select
                    className="dropdown">
                    <option value="DE">DE</option>
                    <option value="EN">EN</option>
                </select>
            </div>
        </div>
    )
}