import {BottomNavigation, BottomNavigationAction} from "@mui/material"
import SearchIcon from '@mui/icons-material/Search';
import ListIcon from '@mui/icons-material/List';
import TuneIcon from '@mui/icons-material/Tune';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import LoginIcon from '@mui/icons-material/Login';
import React, {Component} from "react";


export const BottomNavigation = () => {
    const state = {showSearch: false}


    function _toggleSearch() {
        this.setState({showSearch: !this.state.showSearch});
    }

    return (
        <div style={{width: '100%', position: 'absolute', bottom: '2%', display: 'flex', justifyContent: 'center'}}>
            <BottomNavigation sx={{
                borderRadius: '22px',
            }} value={this.props}
            >

                <BottomNavigationAction label='Search' icon={<SearchIcon/>}
                                        onClick={this.props("search")}/>
                <BottomNavigationAction label='List' icon={<ListIcon/>}/>
                <BottomNavigationAction label='Filter' icon={<TuneIcon/>}/>
                <BottomNavigationAction label='Favorites' icon={<BookmarkIcon/>}/>
                <BottomNavigationAction label='Login' icon={<LoginIcon/>}/>
            </BottomNavigation>
        </div>
    )

}