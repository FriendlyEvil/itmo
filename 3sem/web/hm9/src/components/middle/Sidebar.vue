<template>
    <aside>
        <SidebarPost v-for="post in viewPosts" :post="post" :users="users" :key="post.id"/>
    </aside>
</template>

<script>
    import SidebarPost from '../SidebarPost';
    export default {
        name: "Sidebar",
        props: ['users', 'posts'],
        data: function () {
            return {
                page: "Index"
            }
        },
        components: {
            SidebarPost
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page) => {
                this.page = page;
            });
        }, computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            }
        }
    }
</script>

<style scoped>

</style>