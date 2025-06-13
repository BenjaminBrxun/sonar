import React, {useState} from 'react'
import './App.scss'

function App({children}) {
    return (
        <div className="sonar-body">
            {children}
        </div>
    )
}
export default App
