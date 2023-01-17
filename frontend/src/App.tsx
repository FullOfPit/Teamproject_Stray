import React from 'react';
import './App.css';
import Header from "./components/Header";
import {Route, Routes} from "react-router-dom";
import DashboardPage from './pages/DashboardPage';
import LoginPage from "./pages/LoginPage";
import TripDetailPage from "./pages/TripDetailPage";
import TripFormPage from "./pages/TripFormPage";
import SettingsPage from "./pages/SettingsPage";

function App() {

    //const navigate = useNavigate(); ?

    return (
        <>
            <div className={"header-container"}>
                <Header/>
            </div>
            <main className="page-content container">
                <Routes>
                    <Route path="/" element={<DashboardPage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/trips/:id" element={<TripDetailPage />} />
                    <Route path="/trips/create" element={<TripFormPage />} />
                    <Route path="/settings" element={<SettingsPage/>} />
                </Routes>
            </main>
        </>

    );
}

export default App;
