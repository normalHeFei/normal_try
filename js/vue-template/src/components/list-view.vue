<template>
    <div class="n-listcontainer">
        <list-item v-for="item in list"
                   :key="item.id"
                   v-bind:item="item"
        />
    </div>
</template>

<script>
    import ListItem from "./list-item";

    export default {
        name: "ListView",
        components: {ListItem},
        data() {
            return {
                list: [
                    {title: "title 1", content: "content1", labels: ["label1", "label2"]},
                    {title: "title 2", content: "content2", labels: ["label3", "label4"]}
                ]
            }
        }
    };
</script>

<style scoped>

</style>
