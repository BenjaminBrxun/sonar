import React, {useState} from 'react'
import './App.css'
import {TitleBar} from './components/overlay/TitleBar.jsx'
import {InteractiveMap} from './components/interactivemap/InteractiveMap.jsx'
import {SearchComponent} from './components/search/SearchComponent.jsx'
import {FilterComponent} from './components/filter/FilterComponent.jsx'
import {BottomNavigation, BottomNavigationAction} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import ListIcon from '@mui/icons-material/List';
import TuneIcon from '@mui/icons-material/Tune';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LoginIcon from '@mui/icons-material/Login';
import {FavoritesComponent} from "./components/bookmark/FavoritesComponent.jsx";
import {ListComponent} from "./components/list/ListComponent.jsx";
import {LoginComponent} from "./components/profile/LoginComponent.jsx";


function App() {
    const [page, setPage] = useState("map")

    const [dataFromChild, setDataFromChild] = useState(false);

    function handleDataFromChild(data) {
        setDataFromChild(data);
        setPage("map");
        setDataFromChild(false);
    }

    function _toggle(newPage) {
        if (page === newPage || dataFromChild) {
            setPage("map");
            setDataFromChild(false);
        } else {
            setPage(newPage)
        }
    }

    return (
        <div style={{width:'100vw',height:'100vh'}}>
            <TitleBar/>
            <div className="interactive-map"><InteractiveMap/></div>
            {page === "search" && !dataFromChild && <SearchComponent sendDataToParent={handleDataFromChild}/>}
            {page === "filter" && !dataFromChild && <FilterComponent sendDataToParent={handleDataFromChild}/>}
            {page === "list" && !dataFromChild && <ListComponent sendDataToParent={handleDataFromChild}/>}
            {page === "favorites" && !dataFromChild && <FavoritesComponent sendDataToParent={handleDataFromChild}/>}
            {page === "login" && !dataFromChild && <LoginComponent sendDataToParent={handleDataFromChild}/>}

            <div className="bottom-nav-container">
                <BottomNavigation className="bar">
                    <BottomNavigationAction label='Search' icon={<SearchIcon/>}
                                            onClick={() => _toggle("search")}/>
                    <BottomNavigationAction label='List' icon={<ListIcon/>}
                                            onClick={() => _toggle("list")}/>
                    <BottomNavigationAction label='Filter' icon={<TuneIcon/>}
                                            onClick={() => _toggle("filter")}/>
                    <BottomNavigationAction label='Favorites' icon={<BookmarkIcon/>}
                                            onClick={() => _toggle("favorites")}/>
                    <BottomNavigationAction label='Login' icon={<LoginIcon/>}
                                            onClick={() => _toggle("login")}/>
                </BottomNavigation>
            </div>
        </div>

    )
}


export default App
