import '../styles/globals.css'
import UserLayout from '../components/layouts/UserLayout'

function App({Component, pageProps}) {
    return (
        <UserLayout>
            <Component {...pageProps} />
        </UserLayout>
    )
}

export default App
