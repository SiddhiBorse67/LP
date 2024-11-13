# import ntplib
# from time import ctime, time

# def get_ntp_time(server="pool.ntp.org"):
#     try:
#         # Get the local system time
#         local_time = time()
#         print("Local System Time:", ctime(local_time))
        
#         # Set up the NTP client and make a request
#         client = ntplib.NTPClient()
#         response = client.request(server, version=3)
        
#         # Get the NTP time and offset
#         ntp_time = response.tx_time
#         offset = response.offset  # Time difference between local and NTP time
        
#         # Print the synchronized NTP time and offset
#         print("Synchronized NTP Time:", ctime(ntp_time))
#         print("Offset (seconds):", offset)
        
#         return ntp_time, offset
#     except Exception as e:
#         print("Could not synchronize with NTP server:", e)
#         return None, None

# if __name__ == "__main__":
#     get_ntp_time()



import ntplib
from time import ctime,time

def get_ntp(server="pool.ntp.org"):
    try:
        localtime=time()
        print(f"Local time is ",ctime(localtime))

        client=ntplib.NTPClient()
        response=client.request(server,version=3)

        ntptime=response.tx_time
        offset=response.offset

        print("Synchronized time : ",ctime(ntptime))
        print("Offset :",offset)
    except Exception as e:
        print("Could not Synchronized ",e)



if __name__=="__main__":
    get_ntp()


