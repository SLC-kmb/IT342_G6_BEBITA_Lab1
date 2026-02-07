import { useState } from 'react';
import axios from 'axios';

function Register() {
    const [formData, setFormData] = useState({
        firstName: '', lastName: '', idNumber: '',
        email: '', password: '', userType: 'Student'
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // This connects to your Java Backend on port 3307
            const response = await axios.post('http://localhost:8080/api/auth/register', formData);
            alert("Success: " + response.data);
        } catch (error) {
            alert("Error: " + (error.response?.data || "Connection failed"));
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Registration Test</h2>
            <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px", maxWidth: "300px" }}>
                <input name="firstName" placeholder="First Name" onChange={handleChange} required />
                <input name="lastName" placeholder="Last Name" onChange={handleChange} required />

                {/* Changed type="number" to type="text" to remove arrows */}
                <input
                    name="idNumber"
                    placeholder="ID Number"
                    type="text"
                    pattern="[0-9]*"
                    onChange={handleChange}
                    required
                />

                <input name="email" placeholder="Email" type="email" onChange={handleChange} required />
                <input name="password" placeholder="Password" type="password" onChange={handleChange} required />
                <select name="userType" onChange={handleChange}>
                    <option value="Student">Student</option>
                    <option value="Admin">Admin</option>
                </select>
                <button type="submit">Register User</button>
            </form>

        </div>
    );
}

export default Register;