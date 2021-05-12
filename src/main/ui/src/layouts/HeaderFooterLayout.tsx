import Header from '../components/Header'
import Footer from '../components/Footer'
import LayoutProps from './LayoutProps'
import Box from "@material-ui/core/Box";
import {useSelector} from "react-redux";
import {selectColor} from "../redux/slices/colorSlice";

export default function HeaderFooterLayout({children}: LayoutProps) {

    const {color} = useSelector(selectColor)

    return (
        <Box style={{backgroundColor: `var(--${color ? 'white' : 'dark'}`}}>
            <Header />
            {children}
            <Footer />
        </Box>
    )
}