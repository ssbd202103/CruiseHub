import Box from '@material-ui/core/Box'

export default function Brand() {
    return (
        <Box style={{
            fontFamily: '"Montserrat Alternates", sans-serif',
            fontWeight: 600,
            fontSize: '2rem',
            color: 'var(--white)'
        }}>
            Cruise<span style={{color: 'var(--yellow)'}}>Hub</span>
        </Box>
    )
}