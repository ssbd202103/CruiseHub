import Link from 'next/link'
import en from '../locales/en'
import pl from '../locales/pl'

function SignUp() {
    const lang = pl // this should be taken from account preferences
    return (
        <div>
            <h1>This is the sign up page</h1>

            <label> {lang.signUpPage.firstName} :</label><br/>
            <input type="text" id="firstName"/><br/>
            <label> {lang.signUpPage.lastName} :</label><br/>
            <input type="text" id="lastName"/><br/>
            <label> {lang.signUpPage.login} :</label><br/>
            <input type="text" id="login"/><br/>
            <label> {lang.signUpPage.email} :</label><br/>
            <input type="text" id="email"/><br/>
            <label> {lang.signUpPage.password} :</label><br/>
            <input type="password" id="password"/><br/>
            <label> {lang.signUpPage.confirmPassword} :</label><br/>
            <input type="password" id="password_confirm"/><br/>
            
            <Link href="/">{lang.signUpPage.signUpButton}</Link>
        </div>
    )
}

export default SignUp