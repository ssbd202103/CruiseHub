import Header from '../components/Header'
import Footer from '../components/Footer'

function UserLayout({children}) {
    return (
        <>
            <Header />
            {children}
            <Footer />
        </>
    )
}

export default UserLayout