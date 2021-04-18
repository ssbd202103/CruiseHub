import Link from 'next/link'

function SignUp() {
    return (
        <div>
            <h1>This is the sign up page</h1>

            <label>First name:</label><br/>
            <input type="text" id="firstName"/><br/>
            <label>Last name:</label><br/>
            <input type="text" id="lastName"/><br/>
            <label>Login:</label><br/>
            <input type="text" id="login"/><br/>
            <label>Email:</label><br/>
            <input type="text" id="email"/><br/>
            <label>Password:</label><br/>
            <input type="password" id="password"/><br/>
            <label>Confirm password:</label><br/>
            <input type="password" id="password_confirm"/><br/>
            
            <Link href="/">Sign up (Go to main page for now)</Link>
        </div>
    )
}

export default SignUp