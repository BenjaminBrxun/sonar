import React from 'react';
import {InterfaceFooter} from "../../components/interface/interfaceFooter/InterfaceFooter.jsx";
import {InterfaceHeader} from "../../components/interface/interfaceHeader/InterfaceHeader.jsx";
import {InteractiveMap} from "../../components/interactivemap/InteractiveMap.jsx";
import {navigate} from "vike/client/router";
export { Page };

async function goToFilter() {
    const navigationPromise = navigate('/test')
    console.log("The URL changed but the new page hasn't rendered yet.")
    await navigationPromise
    console.log('The new page has finished rendering.')
}

function Page() {
    return <>
        <InterfaceHeader></InterfaceHeader>
        <InteractiveMap/>
        <InterfaceFooter onFilterClick={goToFilter}></InterfaceFooter>
        </>
}
