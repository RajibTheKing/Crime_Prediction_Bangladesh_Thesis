#include<stdio.h>
#include<iostream>
#include<string>
using namespace std;

int main()
{
    freopen("List_Location.txt", "r", stdin);
    freopen("Done.txt", "w", stdout);
    string str, s;

    while(getline(cin, str))
    {
        s="";
        for(int i=0;i<str.size();i++)
        {
            if(str[i]=='('||str[i]==')'||str[i]==' '||str[i]==' ') continue;
            if(str[i]>='0'&&str[i]<='9') continue;
            s+=str[i];

        }
        cout<<s<<endl;
    }
}
