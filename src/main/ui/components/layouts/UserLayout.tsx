import Header from '../Header'
import Footer from '../Footer'

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