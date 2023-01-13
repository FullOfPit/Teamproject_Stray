import React from 'react';
import './App.css';
import Header from "./components/Header";
import {Route, Routes} from "react-router-dom";
import DashboardPage from './pages/DashboardPage';
import LoginPage from "./pages/LoginPage";
import TripDetailPage from "./pages/TripDetailPage";
import TripFormPage from "./pages/TripFormPage";

function App() {
    return (
        <>
            <Header/>
            <main className="page-content">
              <Routes>
                <Route path="/" element={<DashboardPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/trips/:id" element={<TripDetailPage />} />
                <Route path="/trips/create" element={<TripFormPage />} />
              </Routes>
            </main>
        </>
    );
}

export default App;
