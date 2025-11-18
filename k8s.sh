# 构建Docker镜像
docker build -t demo:latest .

#部署
kubectl apply -f demo-deploy.yaml

# 删除部署
kubectl delete -f demo-deploy.yaml

# 查看部署状态
kubectl describe pod demo-deployment-xxxxxx

