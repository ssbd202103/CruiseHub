import Header from '../components/Header'
import Footer from '../components/Footer'
import LayoutProps from './LayoutProps'

export default function HeaderFooterLayout({children}: LayoutProps) {
    return (
        <>
            <Header />
            {children}
            <Footer />
        </>
    )
}