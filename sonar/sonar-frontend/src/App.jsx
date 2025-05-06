import React, {createContext, useState} from 'react'
import './App.css'
import {TitleBar} from './components/TitleBar.jsx'
import {InteractiveMap} from './components/InteractiveMap.jsx'
import {SearchComponent} from './components/SearchComponent.jsx'
import {FilterComponent} from './components/FilterComponent.jsx'
import {BottomNavigation, BottomNavigationAction} from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import ListIcon from '@mui/icons-material/List';
import TuneIcon from '@mui/icons-material/Tune';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LoginIcon from '@mui/icons-material/Login';
import {FavoritesComponent} from "./components/FavoritesComponent.jsx";
import {ListComponent} from "./components/ListComponent.jsx";
import {LoginComponent} from "./components/LoginComponent.jsx";


function App() {
    const [page, setPage] = useState("map")

    const [dataFromChild, setDataFromChild] = useState(false);
    console.log(dataFromChild);

    function handleDataFromChild(data) {
        setDataFromChild(data);
        console.log('Data from child: ' + dataFromChild);
        setPage("map");
        setDataFromChild(false);
        console.log('Updated data from child: ' + dataFromChild);
    }

    function _toggle(newPage) {
        if(page === newPage || dataFromChild) {
            setPage("map");
            setDataFromChild(false);
        } else {
            setPage(newPage)
        }
    }

    return (
        <>
            <TitleBar/>
            <div style={{position:'absolute', top: 0, zIndex: -2, overflowY: 'hidden'}}><InteractiveMap/></div>
            {page === "search" && !dataFromChild && <SearchComponent sendDataToParent={handleDataFromChild}/> }
            {page === "filter" && !dataFromChild && <FilterComponent sendDataToParent={handleDataFromChild}/>}
            {page === "list" && !dataFromChild && <ListComponent sendDataToParent={handleDataFromChild}/>}
            {page === "favorites" && !dataFromChild && <FavoritesComponent sendDataToParent={handleDataFromChild}/>}
            {page === "login" && !dataFromChild && <LoginComponent sendDataToParent={handleDataFromChild}/>}

            <div style={{width: '100%', position: 'absolute', bottom: '2%', display: 'flex', justifyContent: 'center', zIndex: 0}}>
                <BottomNavigation sx={{
                    borderRadius: '22px',
                }}
                >
                    <BottomNavigationAction label='Search' icon={<SearchIcon/>}
                                            onClick={() => {
                                                _toggle("search");
                                            }}></BottomNavigationAction>
                    <BottomNavigationAction label='List' icon={<ListIcon/>}
                                            onClick={() => _toggle("list")}></BottomNavigationAction>
                    <BottomNavigationAction label='Filter' icon={<TuneIcon/>}
                                            onClick={() => _toggle("filter")}></BottomNavigationAction>
                    <BottomNavigationAction label='Favorites' icon={<BookmarkIcon/>}
                                            onClick={() => _toggle("favorites")}></BottomNavigationAction>
                    <BottomNavigationAction label='Login' icon={<LoginIcon/>}
                                            onClick={() => _toggle("login")}></BottomNavigationAction>
                </BottomNavigation>
            </div>
        </>
    )
}


export default App
