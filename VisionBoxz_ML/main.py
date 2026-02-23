import cv2
import requests
from ultralytics import YOLO

# 1. Model aur Camera Setup
model = YOLO('best.pt')  # Aapki .pt file ka naam
cap = cv2.VideoCapture(0) # 0 matlab laptop webcam, IP camera ke liye URL daalein

# Backend ka URL (Spring Boot)
API_URL = "http://localhost:8080/api/detections/save"

count = 0
track_ids_list = [] # Purane boxes ko yaad rakhne ke liye

while cap.isOpened():
    success, frame = cap.read()
    if success:
        # 2. Tracking ke saath detection (conf=0.5 matlab 50% sure hone par hi dikhayega)
        results = model.track(frame, persist=True, conf=0.5)

        if results[0].boxes.id is not None:
            boxes = results[0].boxes.xyxy.int().cpu().tolist()
            ids = results[0].boxes.id.int().cpu().tolist()

            for box, id in zip(boxes, ids):
                # Naya box detect hone par count badhao
                if id not in track_ids_list:
                    count += 1
                    track_ids_list.append(id)
                    
                    # 3. Backend ko data bhejna
                    try:
                        data = {"boxCount": count, "status": "Loaded"}
                        requests.post(API_URL, json=data)
                    except:
                        print("Backend connect nahi ho pa raha!")

                # Screen par box aur ID dikhana
                cv2.rectangle(frame, (box[0], box[1]), (box[2], box[3]), (0, 255, 0), 2)
                cv2.putText(frame, f"ID: {id}", (box[0], box[1]-10), cv2.FONT_HERSHEY_SIMPLEX, 0.6, (0, 255, 0), 2)

        # Live Count display
        cv2.putText(frame, f"Total Boxes: {count}", (50, 50), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 0, 255), 3)
        cv2.imshow("Box Eye Live", frame)

        if cv2.waitKey(1) & 0xFF == ord("q"):
            break
    else:
        break

cap.release()
cv2.destroyAllWindows()