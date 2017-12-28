def open_file(ii,jj):
    infile = open(str(ii)+"_"+str(jj)+".txt", "r")
    return infile
import string

def remove_punctuation(s):
    s_without_punct = ""
    for letter in s:
        if (letter not in string.punctuation) and (letter not in "\n"):
            s_without_punct = s_without_punct+letter
        else:
            s_without_punct = s_without_punct+""
    return s_without_punct

def count_letter(infile,jj):
    infile.readline()
    while(1):
        line=infile.readline()
        if(line==""):
            break
        if(line==', \n' or line=="\n" or line=="]\n"):
            continue
        if (line==", location:  0\n") or (line=="[location:  0\n"):
            t1=infile.readline()
            t1=remove_punctuation(t1)
            t2 = t1.split(" ")
            print(t2[2])
            t3=infile.readline()
            t3=remove_punctuation(t3)
            t4 = t3.split(" ")
            print(t4[2])
            f=open(t4[2]+"_"+t2[2]+"_mean","a+")
            q=open(t4[2]+"_"+t2[2]+"_var","a+")
        else:
            s=0
            num=0
            t=0.0
            average=0.0
            line=remove_punctuation(line)
            fields = line.split(" ")
            length = len(fields)

##            for i in range(44,length-41):
##                print(i,fields[i])

            for i in range(44,length-41):
                if fields[i]=="":
                    continue
                if (fields[i]=="0" or fields[i]=="-999"):
                    continue
                s+=int(fields[i])
                num=num+1
            #print(num)
            if s==0:
                average=0;
            else:
                average=s/num
            print (average)
            if (jj==11):
                f.write(str(average)+"\n")
            else:
                f.write(str(average)+" ")


            for i in range(44,length-41):
                if fields[i]=="":
                    continue
                if (fields[i]=="0" or fields[i]=="-999"):
                    continue
                #print(i,fields[i])
                t+=(float(fields[i])-average)*(float(fields[i])-average)
            if t==0:
                tm=0;
            else:
                tm=t/num
            print(tm)
            if (jj==11):
                q.write(str(tm)+"\n") 
            else:
                q.write(str(tm)+" ")
            print("\n")

for ii in range (1,6):
    for jj in range (1,12):
        infile=open_file(ii,jj)
        count_letter(infile,jj)
