<template>
    <div id="app">
        <quote-menu></quote-menu>
        <quote-req-list></quote-req-list>
    </div>
</template>

<script>
    import quoteMenu from './components/quote-menu';
    import quoteReqList from './components/quote-req-list';

    export default {
        name: 'App',
        components: {
            quoteMenu,
            quoteReqList
        }
    }
</script>

<style>
    #app {
        display: grid;
        grid-template-columns: 30% 70%;
        justify-content: space-evenly;
        grid-column-gap: 10px;
        align-content: stretch;
    }
</style>
