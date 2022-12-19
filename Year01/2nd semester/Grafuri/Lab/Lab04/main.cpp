#include <iostream>
#include <vector>

using namespace std;

const int Maxx=1e5+1;
int father[Maxx];

bool dead[Maxx];
bool vis[Maxx];

vector<int> puffer;

int main() {
    int n,root;
    cin>>n;
    for(int i=0; i<n; ++i){
        cin>>father[i];
        if(father[i]==-1){
            root=i;
            continue;
        }
        vis[father[i]]=1;
    }
    int elim=0;
    while(elim!=n){
        int mn=-1;
        for(int i=0; i<n; ++i){
            mn=i;
            if(!vis[i] || !dead[i]) break;
        }
        elim++;
        puffer.push_back(father[mn]);
        dead[mn]=1;
        father[mn]=-2;
        for(int i=0; i<n; ++i) vis[i]=0;
        for(int i=0; i<n; ++i){
            if(father[i]>=0){
                vis[father[i]]=1;
            }
        }
    }



    return 0;
}
