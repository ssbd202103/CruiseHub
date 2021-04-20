import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/styles'

export interface RoundedButtonProps {
    readonly className?: string,
    readonly color: "pink" | "yellow" | "green" | "blue"
    readonly children?: any,
}

export default function RoundedButton(props: RoundedButtonProps) {
    
    const {
        className: styles,
        children,
        color
    } = props

    const classes = makeStyles(theme => ({
        root: {
            fontFamily: "'Montserrat', sans-serif",
            fontSize: '1.2rem',
            backgroundColor: 'var(--' + color + ')',
            color: 'var(--white)',
            height: 68,
            borderRadius: 34,
            '&:hover': {
                backgroundColor: 'var(--' + color + '-dark)'
            }
        }
    }))()

    return (
        <Button 
            className={(styles || '') + ' ' + classes.root}
        >{children}</Button>
    )
}