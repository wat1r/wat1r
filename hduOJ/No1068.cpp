#include <iostream>
#include <stdio.h>
#include <string.h>
using namespace std;
#define MAX 1005
/**
AC Code
*/
bool used[MAX];
int linked[MAX];
int g[MAX][MAX];
int n;
bool dfs(int u)
{
    int v;
    for (v=0;v<n;v++)
    {
        if (g[u][v]&&!used[v])
        {
            used[v] = true;
            if (linked[v]==-1||dfs(linked[v]))
            {
                linked[v] = u;
                return true;
            }
        }
    }
    return false;
}


int hungry()
{
    int u;
    int result=0;
    memset(linked,-1,sizeof(linked));
    for (u=0;u<n;u++)
    {
        memset(used,0,sizeof(used));
        if (dfs(u))result++;
    }
    return result;
}
int main()
{
    int u,v;
    int num;
    while (cin>>n)
    {
        memset(g,0,sizeof(g));
        for (int i=0;i<n;i++)
        {
            scanf("%d: (%d)",&u,&num);
            for (int j=0;j<num;j++)
            {
                cin>>v;
                g[i][v] = 1;
            }
        }
        cout<<n-hungry()/2<<endl;
    }
    return 0;
}