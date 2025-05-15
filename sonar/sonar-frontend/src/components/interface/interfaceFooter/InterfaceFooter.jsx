import "./InterfaceFooter.scss"
import SearchIcon from '@mui/icons-material/Search';
import ListIcon from '@mui/icons-material/List';
import TuneIcon from '@mui/icons-material/Tune';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LoginIcon from '@mui/icons-material/Login';

export function InterfaceFooter() {
    return (
        <div className="sonar-footer">
            <div className="sonar-footer_inner">

                <ul className="sonar-footer_menu">
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <SearchIcon/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <ListIcon/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <TuneIcon/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <BookmarkIcon/>
                        </button>
                    </li>
                    <li className="sonar-footer_menu-seperator"></li>
                    <li className="sonar-footer_menu-item">
                        <button className="sonar-footer_menu-button">
                            <LoginIcon/>
                        </button>
                    </li>
                </ul>

            </div>
        </div>
    )
}