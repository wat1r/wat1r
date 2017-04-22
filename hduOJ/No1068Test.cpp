#include <iostream>
#include <cstdio>
using namespace std;
const int N =505;
int map[N][N],n,match[N];
bool visited[N];

int find(int x)
{
    for (int i =0;i<n;i++)
    {
        if (map[x][i]==1&&!visited[i])
        {
            visited[i]=1;
            if (match[i]==-1||find(match[i]))
            {
                match[i]=x;
                return true;
            }
        }
    }
    return false;
}
int main()
{
    while (cin>>n)
    {
        int num,x,y;
        memset (map,0,sizeof (map));
        memset (match,-1,sizeof (match));
        for (int j=0;j<n;j++)
        {
            scanf ("%d: (%d)",&x,&num);
            for (int i=0;i<num;i++)
            {
                cin>>y;
                map[x][y]=1;
            }
        }
        int cnt=0;
        for (int i=0;i<n;i++)
        {
            memset (visited,0,sizeof (visited));
            if (find(i))
                cnt++;
        }
        printf ("%d\n",n-cnt/2);
        return 0;

    }
}