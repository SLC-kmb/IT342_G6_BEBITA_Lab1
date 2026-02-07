import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Check if user is logged in
        const loggedInUser = localStorage.getItem('user');
        if (loggedInUser) {
            setUser(JSON.parse(loggedInUser));
        } else {
            // If not logged in, kick them back to Login page
            navigate('/login');
        }
    }, [navigate]);

    const handleLogout = () => {
        localStorage.removeItem('user'); // Delete session
        navigate('/login');
    };

    if (!user) return <h2>Loading...</h2>;

    return (
        <div style={{ padding: "20px" }}>
            <h1>Welcome, {user.firstName}!</h1>
            <p>Role: {user.userType}</p>
            <p>Email: {user.email}</p>

            <div style={{ marginTop: "20px", border: "1px solid #ccc", padding: "10px" }}>
                <h3>Student Dashboard</h3>
                <p>Here you can view your schedule, grades, etc.</p>
            </div>

            <button onClick={handleLogout} style={{ marginTop: "20px", backgroundColor: "red", color: "white" }}>
                Logout
            </button>
        </div>
    );
}

export default Dashboard;